package de.metas.ui.web.vaadin.window.datasource;

import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_AD_User_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_Bill_BPartner_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_Bill_Location_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_Bill_User_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_C_BPartner_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_C_BPartner_Location_ID;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_DatePromised;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_DocumentNo;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_Lines;
import static de.metas.ui.web.vaadin.window.HARDCODED_Order.ORDER_M_Warehouse_ID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.PropertyName;
import de.metas.ui.web.vaadin.window.datasource.sql.ModelDataSourceQuery;
import de.metas.ui.web.vaadin.window.editor.LookupValue;
import de.metas.ui.web.vaadin.window.model.PropertyValuesDTO;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DummyModelDataSource implements ModelDataSource
{
	private static final Logger logger = LogManager.getLogger(DummyModelDataSource.class);

	private List<PropertyValuesDTO> records = new ArrayList<>();
	
	public DummyModelDataSource(final PropertyDescriptor rootPropertyDescriptor)
	{
		super();
		
		for (int i = 0; i < 10; i++)
		{
			records.add(createDummyRecord(i));
		}
	}

	@Override
	public PropertyValuesDTO getRecord(final int index)
	{
		if (index < 0 || index >= records.size())
		{
			throw new IllegalArgumentException("No record found at index " + index);
		}
		
		final PropertyValuesDTO values = records.get(index);
		logger.debug("Get record {}: {}", index, values);
		return values;
	}

	private PropertyValuesDTO createDummyRecord(final int index)
	{
		final PropertyValuesDTO.Builder record = PropertyValuesDTO.builder();
		record.put(ORDER_DocumentNo, "1000-" + index);
		record.put(ORDER_DatePromised, TimeUtil.getDay(2016, 4 + 1, 24));
		record.put(ORDER_M_Warehouse_ID, LookupValue.of(540008, "Hauptlager"));

		record.put(ORDER_C_BPartner_ID, LookupValue.of(1234, "G0105 - ABC Vegetables"));
		record.put(ORDER_C_BPartner_Location_ID, LookupValue.of(1234, "Johannes-R.-Becher-Straße 3-11"));
		record.put(ORDER_AD_User_ID, LookupValue.of(1234, "Max Mustermann"));

		record.put(ORDER_Bill_BPartner_ID, LookupValue.of(1234, "Bill bpartner"));
		record.put(ORDER_Bill_Location_ID, LookupValue.of(1234, "Bill address"));
		record.put(ORDER_Bill_User_ID, LookupValue.of(1234, "Bill user"));

		//
		// Lines
		final List<PropertyValuesDTO> lines = new ArrayList<>();
		for (int i = 1; i <= 10; i++)
		{
			final PropertyValuesDTO.Builder line = PropertyValuesDTO.builder();
			line.put(PropertyName.of("LineNo"), i * 10);
			line.put(PropertyName.of("M_Product_ID"), LookupValue.of(1, index + " - Alice salad " + i));
			line.put(PropertyName.of("M_AttributeSetInstance_ID"), LookupValue.of(1, "BIO"));
			line.put(PropertyName.of("QtyTU"), BigDecimal.valueOf(i));
			line.put(PropertyName.of("M_HU_PI_Item_Product_ID"), LookupValue.of(1, "IFCO"));
			lines.add(line.build());
		}
		record.put(ORDER_Lines, lines);

		return record.build();
	}

	@Override
	public int getRecordsCount()
	{
		return records.size();
	}

	@Override
	public SaveResult saveRecord(final int index, final PropertyValuesDTO values)
	{
		if (index < 0 || index >= records.size())
		{
			throw new IllegalArgumentException("No record found at index " + index);
		}
		
		logger.debug("Saving record {}: {}", index, values);
		
		records.set(index, PropertyValuesDTO.copyOf(values));
		Object recordId = null; // unknown
		return SaveResult.of(index, recordId);
	}

	@Override
	public Supplier<List<PropertyValuesDTO>> retrieveRecordsSupplier(final ModelDataSourceQuery query)
	{
		return Suppliers.memoize(new Supplier<List<PropertyValuesDTO>>(){

			@Override
			public List<PropertyValuesDTO> get()
			{
				return ImmutableList.copyOf(records);
			}
		});
	}

	@Override
	public PropertyValuesDTO retrieveRecordById(Object recordId)
	{
		throw new UnsupportedOperationException();
	}
}
