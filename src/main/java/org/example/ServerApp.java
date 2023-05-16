package org.example;

import reactor.netty.http.server.HttpServer;

public class ServerApp {

	public static void main(String[] args) {
		HttpServer.create()
				.port(8080)
				.route(routes -> routes.ws("/ws", (in, out) ->
						in.receive().asString().doOnNext(s -> System.out.println("RECEIVED " + s)).then()))
				.bindNow()
				.onDispose()
				.block();
	}
}
