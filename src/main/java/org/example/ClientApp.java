package org.example;

import io.netty.buffer.PooledByteBufAllocator;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.websocket.WebsocketOutbound;

public class ClientApp {

	public static void main(String[] args) {
		HttpClient.create()
				.wiretap(true)
				.websocket()
				.uri("ws://localhost:8080/ws")
				.handle((websocketInbound, websocketOutbound) -> {
					sendText(websocketOutbound);
					return websocketOutbound.neverComplete();
				})
				.blockLast();
	}

	private static void sendText(WebsocketOutbound websocketOutbound) {
		new Thread(() -> {
			while (true) {
				websocketOutbound.send(Mono.just(PooledByteBufAllocator.DEFAULT.buffer().writeBytes("1231313".getBytes()))).then().subscribe();
			}
		}).start();
	}
}
