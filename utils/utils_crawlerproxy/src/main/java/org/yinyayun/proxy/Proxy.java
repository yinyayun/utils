package org.yinyayun.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

public class Proxy {
	private final static String IP_PORT_REGEX = "((1?\\d{1,2}|2[1-5][1-5])\\.){3}(1?\\d{1,2}|2[1-5][1-5]):([1-5]?\\d{1,4}|6[1-5][1-5][1-3][1-5])";
	private final static String IP_REGEX = "((1?\\d{1,2}|2[1-5][1-5])\\.){3}(1?\\d{1,2}|2[1-5][1-5])";

	public static void main(String[] args) {
		Proxy proxy = new Proxy();
		proxy.setProxy();
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.veryhuo.com/res/ip/");
		for (String url : urls) {
			// proxy.parser("http://www.veryhuo.com/res/ip/");
			proxy.nodeIter(1, url);
		}
	}

	public void parser(String url) {
		try {
			Parser parser = new Parser(url);
			// NodeFilter regfilter=new RegexFilter(IP_REGEX);
			NodeFilter filter = new StringFilter(IP_PORT_REGEX);
			NodeList nodeList = parser.parse(filter);
			Node[] nodes = nodeList.toNodeArray();
			for (Node node : nodes) {
				System.out.println(node.getText());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void nodeIter(int deep, String url) {
		try {
			if (deep == 1) {
				Parser parser = new Parser(url);
				NodeList list = parser.parse(new StringFilter());
				Node[] nodes = list.toNodeArray();
				for (Node node : nodes) {
					if (node.getText().matches(IP_PORT_REGEX)
							|| node.getText().matches(IP_REGEX)) {
						System.out.println(node.getText());
					}
				}
			} else {
				Parser parser = new Parser(url);
				// parser.parse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProxy() {
		System.setProperty("http.proxyHost", "192.168.16.189");
		System.setProperty("http.proxyPort", "8080");
	}
}
