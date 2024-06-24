/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- 2023-11-07T17:01:11.207Z
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2023-11-07 19:01:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N','N','N',540127,'External Lot-Number',TO_TIMESTAMP('2023-11-07 19:01:11','YYYY-MM-DD HH24:MI:SS'),100,'ExternalLotNumber')
;


INSERT INTO M_HU_PI_Attribute (AD_Client_ID, AD_Org_ID, Created, CreatedBy, HU_TansferStrategy_JavaClass_ID, splitterstrategy_javaclass_id, IsActive,
                               IsDisplayed, IsInstanceAttribute, IsMandatory, IsOnlyIfInProductAttributeSet, IsReadOnly,
                               M_Attribute_ID, M_HU_PI_Attribute_ID, M_HU_PI_Version_ID, PropagationType, SeqNo,
                               Updated, UpdatedBy, UseInASI)
VALUES (1000000, 1000000, TO_TIMESTAMP('2023-11-07 19:11:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 540027, 540017, 'Y', 'Y', 'N', 'N', 'N', 'N',
        540127, 540115, 100, 'TOPD', 9110, TO_TIMESTAMP('2023-11-07 19:11:56', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y')
;
