/*
 * #%L
 * de.metas.business
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

UPDATE ad_ref_list SET IsActive='N', updated='2022-06-20 12:10', updatedby=99,
                       description='2022-06-20 metas-ts: deactivate because they are commented out in org.eevolution.api.BOMComponentType'
where AD_Reference_ID=53225
  and Value in ('PL'/*Planning*/,
                'OP'/*Option*/,
                'SC'/*Scrap*/,
                'PR' /*Product*/)
  and IsActive='Y';