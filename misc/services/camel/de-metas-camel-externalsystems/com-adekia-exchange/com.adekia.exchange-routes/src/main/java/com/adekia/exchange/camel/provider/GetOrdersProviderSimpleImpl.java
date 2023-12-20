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

package com.adekia.exchange.camel.provider;

import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.provider.GetOrdersProvider;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@ConditionalOnSingleCandidate(GetOrdersProvider.class)
public class GetOrdersProviderSimpleImpl implements GetOrdersProvider {
    private final GetOrderProviderSimpleImpl orderProvider;

    @Autowired
    public GetOrdersProviderSimpleImpl(final GetOrderProviderSimpleImpl orderProvider)
    {
        this.orderProvider = orderProvider;
    }

    @Override
    public List<OrderType> getOrders(Ctx ctx) throws Exception {
        return new Random().ints()
                .limit(ThreadLocalRandom.current().nextInt(5))
                .mapToObj(i -> orderProvider.getOrder(ctx))
                .collect(Collectors.toList());
    }
}
