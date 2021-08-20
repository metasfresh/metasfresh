/*
 * #%L
 * de.metas.acct.base
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


INSERT INTO public.c_taxcategory (c_taxcategory_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, commoditycode, vattype, internalname, producttype)
select 1000009, 1000000, 1000000, 'Y', '2015-11-09 15:56:15.000000', 100, '2021-06-10 13:41:56.000000', 100, 'Normale MWSt', null, null, null, 'Normal', 'I'
where not exists (select 1 from c_taxcategory where c_taxcategory_id=1000009);

INSERT INTO public.c_taxcategory (c_taxcategory_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, commoditycode, vattype, internalname, producttype)
select 1000010, 1000000, 1000000, 'Y', '2015-11-09 15:56:24.000000', 100, '2021-06-10 13:44:06.000000', 100, 'Reduzierte MWSt', null, null, null, 'Reduced', 'I'
where not exists (select 1 from c_taxcategory where c_taxcategory_id=1000010);
