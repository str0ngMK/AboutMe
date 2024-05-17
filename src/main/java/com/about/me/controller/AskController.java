package com.about.me.controller;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.about.me.service.AskService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/chat")
@Log4j2
public class AskController {

	@Autowired
	private AskService askService;

	@GetMapping("/ask")
	public ResponseEntity<Map<String, Object>> getMessage(HttpServletRequest request, @RequestParam(name="choice") long choice) {
		String clientIp = request.getRemoteAddr();
		String ipv4 = convertToIPv4(clientIp);
		log.info("[ASK] 호출 IP: " + ipv4);
		return ResponseEntity.ok().body(askService.message(choice));
	}
	
	private String convertToIPv4(String ipv6) {
		try {
            InetAddress inetAddress = InetAddress.getByName(ipv6);
            if (inetAddress instanceof Inet6Address) {
                byte[] bytes = inetAddress.getAddress();
                return InetAddress.getByAddress(Arrays.copyOfRange(bytes, 12, 16)).getHostAddress();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipv6;
	}
}
