package com.about.me.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class IpConversion {
	public String convertToIPv4(String ipv6) {
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
