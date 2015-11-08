package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import dto.TimeSeries;
import play.libs.F;
import play.libs.F.Promise;
//import play.api.libs.concurrent.Promise;
import play.libs.ws.WSClient;
//import play.api.libs.ws.WSClient
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.html.*;

public class Application extends Controller {
	@Inject
	WSClient ws;

	// public Result index() {
	// return ok(index.render("Your new application is ready."));
	// }

	public Result indexconnectavo() {

		return ok(landing.render("Your new application is ready."));
	}

	public Result data(String source, Long fromTime) {
		TimeSeries timeSeries = new TimeSeries();
		timeSeries.setValue((double) Math.random() * 10000 % 1000);
		return ok(timeSeries);
	}

	public Result data2(String target, String from, String format) {
		final String feedUrl = "http://ec2-54-179-134-162.ap-southeast-1.compute.amazonaws.com:3000/api/datasources/proxy/1/render?format="
				+ format + "&from=" + from + "&format=" + format;
		// + "&jsoncallback=?";
		// String url = "http://www.google.com/search?q=httpClient";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(feedUrl);
		// add request header
		// request.addHeader("User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
		// request.addHeader("Access-Control-Allow-Origin","*");
		// request.setHeader("accept", "application/json");

		request.addHeader("Access-Control-Allow-Credentials", "true");
		request.addHeader("Access-Control-Allow-Headers:Authorization",
				"Content-Type");
		// request.addHeader("Access-Control-Allow-Methods,"GET, POST,
		// OPTIONS");
		request.addHeader("Access-Control-Allow-Origin", "*");
		request.addHeader("Cache-Control", "no-cache");
		request.addHeader("Content-Encoding", "gzip");
		//request.addHeader("Content-Length", "134");
		request.addHeader("Content-Type", "application/json");
		//request.addHeader("Date", "Sun, 08 Nov 2015 12:40:20 GMT");
		request.addHeader("Pragma", "no-cache");
		//request.addHeader("Server", "nginx/1.4.6 (Ubuntu)");
		request.addHeader("Vary", "Accept-Encoding");

		//request.addHeader(arg0, arg1);
		HttpResponse response;
		try {
			response = client.execute(request);

			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return ok(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok("{}");
	}

}
