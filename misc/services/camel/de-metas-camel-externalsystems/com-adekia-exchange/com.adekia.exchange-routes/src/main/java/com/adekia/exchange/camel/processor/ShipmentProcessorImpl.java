/*
 * #%L
 * exchange-routes
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
import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.sender.ShipmentSender;
import com.adekia.exchange.transformer.ShipmentTransformer;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_23.DespatchAdviceType;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnSingleCandidate(ShipmentProcessorImpl.class)
public class ShipmentProcessorImpl implements ShipmentProcessor
{

    private final ShipmentTransformer shipmentTransformer;
    private final ShipmentSender shipmentSender;

    @Autowired
    public ShipmentProcessorImpl(
            ShipmentTransformer shipmentTransformer,
            ShipmentSender shipmentSender)
    {
        this.shipmentTransformer = shipmentTransformer;
        this.shipmentSender = shipmentSender;

    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        Ctx ctx = exchange.getProperty(Ctx.CAMEL_PROPERTY_NAME, Ctx.class);
        SimpleLog logger = SimpleLog.getLogger(exchange);
        logger.clear();

        DespatchAdviceType despatchAdvice = exchange.getIn().getBody(DespatchAdviceType.class);
        Object transformedDa = shipmentTransformer.transform(despatchAdvice);
        logger.add(transformedDa.toString());

        String response = shipmentSender.send(ctx, transformedDa);
        logger.add(response);

        exchange.getIn().setBody(despatchAdvice);
    }
}
