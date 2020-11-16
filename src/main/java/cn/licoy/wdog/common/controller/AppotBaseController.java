package cn.licoy.wdog.common.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppotBaseController {
    public String base_url = "http://www.yushangcc.com";
//    public String base_url = "http://127.0.0.1";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法
}
