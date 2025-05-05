/*
 * #%L
 * camel-routes
 * %%
 * Copyright (C) 2022 Adekia
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package com.adekia.exchange.camel.processor;

import com.adekia.exchange.camel.logger.SimpleLog;
import com.adekia.exchange.sender.OrderSender;
import com.adekia.exchange.transformer.OrderTransformer;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnSingleCandidate(OrderProcessor.class)
public class OrderProcessorImpl implements OrderProcessor
{

    private final OrderTransformer orderTransformer;
    private final OrderSender orderSender;

    @Autowired
    public OrderProcessorImpl(
            OrderTransformer orderTransformer,
            OrderSender orderSender) {
        this.orderTransformer = orderTransformer;
        this.orderSender=orderSender;
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        SimpleLog logger = SimpleLog.getLogger(exchange);
        logger.clear();

        OrderType order = exchange.getIn().getBody(OrderType.class);
        Object transformedOrder = orderTransformer.transform(order);
        logger.add(transformedOrder.toString());

        String response = orderSender.send(transformedOrder);
        logger.add(response);

        //exchange.getIn().setBody(order);    //todo needed?
    }
}
