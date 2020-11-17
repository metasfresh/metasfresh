/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport.compudata;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class P100 implements Serializable
{
	private static final long serialVersionUID = 5689176109531323064L;

	private String record;
	private String partner;
	private String messageNo;
	private String positionNo;
	private String eanArtNo;
	private String buyerArtNo;
	private String supplierArtNo;
	private String artDescription;
	private String deliverQual;
	private String deliverQTY;
	private String deliverUnit;
	private String orderNo;
	private String orderPosNo;
	private String cUperTU;
	private String currency;
	private String detailPrice;
	private Date deliveryDate;
	private Date bestBeforeDate;
	private String chargenNo;
	private String articleClass;
	private String differenceQTY;
	private String discrepancyCode;
	private Date diffDeliveryDate;
	private String eanTU;
	private String storeNumber;
	private String unitCode;
	private Date sellBeforeDate;
	private Date productionDate;
	private String discrepancyText;
	private String grainItemNummer;
}
