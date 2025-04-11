/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-04-11T05:51:39.588Z
UPDATE ES_FTS_Config SET ES_CreateIndexCommand='{ "settings": { "analysis": { "filter": { "german_stop": { "type": "stop", "stopwords": "_german_" } }, "analyzer": { "standard_analyzer": { "type": "custom", "tokenizer": "standard", "filter": [ "lowercase", "german_stop", "german_normalization" ] } } } }, "mappings": { "properties": { "c_invoice_id": { "type": "keyword" }, "c_bpartner_id": { "type": "keyword" }, "c_bpartner_location_id": { "type": "keyword" }, "ad_user_id": { "type": "keyword" }, "c_doctype_id": { "type": "keyword" }, "m_warehouse_id": { "type": "keyword" }, "c_calendar_id": { "type": "keyword" }, "c_year_id": { "type": "keyword" }, "documentno": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "poreference": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "bpartnervalue": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "externalid": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "bpname": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "firstname": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "lastname": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "companyname": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "address1": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "city": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "postal": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "doctypename": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "warehousename": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "calendarname": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "fiscalyear": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "description": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "descriptionbottom": { "type": "text", "analyzer": "standard_analyzer", "term_vector": "with_positions_offsets" }, "ispaid": { "type": "boolean" }, "ispartiallypaid": { "type": "boolean" } } } }',Updated=TO_TIMESTAMP('2025-04-11 07:51:39.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540003
;

-- 2025-04-11T05:42:40.555Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-04-11 07:42:40.433','YYYY-MM-DD HH24:MI:SS.US'),100,'ispaid',540069,540003,'Y',TO_TIMESTAMP('2025-04-11 07:42:40.433','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-11T05:42:40.687Z
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2025-04-11 07:42:40.59','YYYY-MM-DD HH24:MI:SS.US'),100,'ispartiallypaid',540070,540003,'Y',TO_TIMESTAMP('2025-04-11 07:42:40.59','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-04-11T05:46:34.104Z
UPDATE ES_FTS_Config
SET ES_QueryCommand='{
	"_source": true,
	"query": {
		"bool": {
			"must": [
				{
					"bool": {
						"should": [
							{
								"wildcard": {
									"firstname": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"lastname": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"bpname": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"companyname": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"doctypename": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"calendarname": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"warehousename": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"postal": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"city": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"fiscalyear": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"documentno": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"wildcard": {
									"poreference": {
										"value": @queryStartsWith@
									}
								}
							},
							{
								"multi_match": {
									"query": @query@,
									"type": "cross_fields",
									"fields": [
										"documentno",
										"poreference",
										"bpartnervalue",
										"externalid",
										"bpname",
										"firstname",
										"lastname",
										"companyname",
										"address1",
										"city",
										"postal",
										"doctypename",
										"warehousename",
										"calendarname",
										"fiscalyear",
										"description",
										"descriptionbottom"
									],
									"operator": "and"
								}
							}
						]
					}
				}
				@DefaultFilterQuery@
			]
		}
	}
}',
    Updated=TO_TIMESTAMP('2025-04-11 07:46:34.104', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE ES_FTS_Config_ID = 540003
;