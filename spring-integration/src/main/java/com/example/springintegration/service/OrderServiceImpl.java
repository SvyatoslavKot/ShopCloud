package com.example.springintegration.service;

import com.example.springintegration.domain.Order;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    @Transactional
    public void save(Order order) {
        File orderFolder = new File("C:\\Users\\NADEZHDA\\IdeaProjects\\ShopCloud\\orders");

        File orderFile = new File(orderFolder, order.getOrderId() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(orderFile, order);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
