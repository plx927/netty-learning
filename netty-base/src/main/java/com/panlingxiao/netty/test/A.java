package com.panlingxiao.netty.test;

import com.panlingxiao.netty.client.NettyRemotingClient;
import com.panlingxiao.netty.service.NettyConfig;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by panlingxiao on 2018/6/20.
 */
@Slf4j
public class A {

    public static void main(String[] args) {

        NettyRemotingClient client = new NettyRemotingClient(new NettyConfig());
        client.start();

        JFrame frame = new JFrame();
        frame.setTitle("测试");
        JPanel p1 = new JPanel();
        frame.add(p1);
        p1.add(new JLabel("服务器地址:"));
        JTextField t1 = new JTextField("127.0.0.1:8080");
        p1.add(t1);
        p1.add(new JLabel("消息内容"));
        JTextField t2 = new JTextField("hello world!");
        p1.add(t2);

        JPanel p2 = new JPanel();
        JButton button = new JButton("同步调用");
        p2.add(button);

        JButton button2 = new JButton("异步调用");
        p2.add(button2);
        frame.add(p2, BorderLayout.SOUTH);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = t1.getText();
                String message = t2.getText();
                try {
                    String result = client.invokeSync(address, message, 3000);
                    log.info("Invoke sync success,result:{}", result);
                } catch (Exception e1) {
                    log.error("invokeSync fail:", e1);
                }
            }
        });

    }
}
