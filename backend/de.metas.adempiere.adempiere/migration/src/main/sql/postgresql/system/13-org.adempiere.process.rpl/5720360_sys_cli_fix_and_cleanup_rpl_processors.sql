
/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2024 metas GmbH
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

/*fix exchangeName to make the exchange declared in camel-edi*/
update imp_processorparameter set value='exchangeName', parametervalue='de.metas.esb.to.metasfresh.durable', updated='2024-03-27 15:48:00', updatedby=99 where imp_processorparameter_id=540024;    

-- actually this is the original and exp_processor_id=540014 is a duplicate, but in our recent DB-images, we have only the duplicate.
delete from exp_processorparameter where exp_processor_id=540011;
delete from exp_processor where exp_processor_id=540011;

/*fix exchangeName to make the exchange declared in camel-edi*/
update exp_processorparameter set value='exchangeName', parametervalue='de.metas.esb.from.metasfresh.durable' , updated='2024-03-27 15:48:00', updatedby=99 where exp_processorparameter_id=540036;



                             