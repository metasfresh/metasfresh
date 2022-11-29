
/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- select * from ad_element where columnname='QtyInventoryCount';
-- select * from ad_element_trl where ad_element_id=579903; ---> de_CH should be fine
update ad_element set name='Inventurbestand', printname='Inventurbestand', description='Bestand laut der letzten Inventur' where ad_element_id=579903;

-- select * from ad_element where columnname='QtySupplyRequired';
-- select * from ad_element_trl where ad_element_id=579898;
update ad_element set name='Bedarfssumme', printname='Bedarfssumme', description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.' where ad_element_id=579898;

-- select * from ad_element where columnname='QtySupplyToSchedule';
-- select * from ad_element_trl where ad_element_id=579899;
update ad_element set name='Zu disp. Bedarf', printname='Zu disp. Bedarf', description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.' where ad_element_id=579899;

-- select * from ad_element where columnname='QtyStockCurrent';
-- select * from ad_element_trl where ad_element_id=579905;
update ad_element set name='Planbestand', printname='Planbestand' where ad_element_id=579905;
update ad_element_trl set name='Planbestand', printname='Planbestand' where ad_element_id=579905 and ad_language='de_CH';

-- select * from ad_element where columnname='QtyOnHand';
-- select * from ad_element_trl where ad_element_id=530;
update ad_element set description='Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.', help=null where ad_element_id=530;
update ad_element_trl set description='Bestand gibt die Menge des Produktes an, die im Lager verfügbar ist.', help=null where ad_element_id=530 and ad_language in ('de_DE','de_CH');