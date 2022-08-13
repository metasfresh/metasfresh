/*
 * #%L
 * exchange-int
 * %%
 * Copyright (C) 2022 metas GmbH
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

<<<<<<<< HEAD:misc/services/camel/de-metas-camel-externalsystems/com-adekia-exchange/com.adekia.exchange-int/src/main/java/com/adekia/exchange/context/OrderCtx.java
package com.adekia.exchange.context;

import lombok.Builder;
import lombok.Data;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
========
package com.adekia.exchange.transformer;

import oasis.names.specification.ubl.schema.xsd.despatchadvice_23.DespatchAdviceType;
>>>>>>>> 687d3e71b3 (Backup / Refactoring / Shipping Amazon side / Ctx):misc/services/camel/de-metas-camel-externalsystems/com-adekia-exchange/com.adekia.exchange-int/src/main/java/com/adekia/exchange/transformer/ShipmentTransformer.java

public interface ShipmentTransformer
{
    public Object transform(final DespatchAdviceType despatchAdvice) throws Exception;
}
