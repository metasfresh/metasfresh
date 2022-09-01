-- 2022-04-12T06:35:43.456Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000018
;

-- 2022-04-12T06:35:43.460Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000018
;

-- 2022-04-12T06:36:12.563Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000023
;

-- 2022-04-12T06:36:12.565Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000023
;

-- 2022-04-12T06:36:17.148Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000022
;

-- 2022-04-12T06:36:17.149Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000022
;

-- 2022-04-12T06:36:21.121Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000021
;

-- 2022-04-12T06:36:21.122Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000021
;

-- 2022-04-12T06:36:25.163Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000015
;

-- 2022-04-12T06:36:25.164Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000015
;

-- 2022-04-12T06:36:29.727Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000016
;

-- 2022-04-12T06:36:29.728Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000016
;

-- 2022-04-12T06:37:07.222Z
UPDATE WEBUI_DashboardItem SET SeqNo=10,Updated=TO_TIMESTAMP('2022-04-12 08:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_DashboardItem_ID=1000024
;

-- 2022-04-12T06:37:18.507Z
DELETE FROM  WEBUI_DashboardItem_Trl WHERE WEBUI_DashboardItem_ID=1000019
;

-- 2022-04-12T06:37:18.510Z
DELETE FROM WEBUI_DashboardItem WHERE WEBUI_DashboardItem_ID=1000019
;


-- 2022-04-11T07:43:01.014Z
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_Details_WhereClause,SQL_From,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2022-04-11 09:43:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',1,'S','Nettosumme der Kundenaufträge für heute.',259,'c_order.AD_Client_ID = @AD_Client_ID@ AND c_order.AD_Org_ID=@AD_Org_ID@','c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id','docbasetype = ''SOO'' and co.created > now()::date - INTERVAL ''1 day''',TO_TIMESTAMP('2022-04-11 09:43:00','YYYY-MM-DD HH24:MI:SS'),100,540036)
;

-- 2022-04-11T07:44:03.560Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,541184,0,12,TO_TIMESTAMP('2022-04-11 09:44:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Net Sum','select sum(x.grandtotal) from (SELECT grandtotal FROM c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id WHERE docbasetype = ''SOO'' AND co.created > now()::date - INTERVAL ''1 day'' )as x',TO_TIMESTAMP('2022-04-11 09:44:03','YYYY-MM-DD HH24:MI:SS'),100,540057,540036)
;

-- 2022-04-11T07:44:03.560Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540057 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T07:44:56.032Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,577559,0,10,TO_TIMESTAMP('2022-04-11 09:44:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Currency','cy.iso_code',TO_TIMESTAMP('2022-04-11 09:44:55','YYYY-MM-DD HH24:MI:SS'),100,540058,540036)
;

-- 2022-04-11T07:44:56.035Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540058 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T07:48:32.180Z
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_Details_WhereClause,SQL_From,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2022-04-11 09:48:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',1,'S','Nettosumme der Kundenaufträge der vergangenen Woche.',259,'c_order.AD_Client_ID = @AD_Client_ID@ AND c_order.AD_Org_ID=@AD_Org_ID@','c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id','docbasetype = ''SOO'' AND co.created > now()::date - INTERVAL ''7 day''',TO_TIMESTAMP('2022-04-11 09:48:32','YYYY-MM-DD HH24:MI:SS'),100,540037)
;

-- 2022-04-11T07:49:31.451Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,541184,0,12,TO_TIMESTAMP('2022-04-11 09:49:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Net Sum','select sum(x.grandtotal) from (SELECT grandtotal FROM c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id WHERE docbasetype = ''SOO'' AND co.created > now()::date - INTERVAL ''7 day'' )as x',TO_TIMESTAMP('2022-04-11 09:49:31','YYYY-MM-DD HH24:MI:SS'),100,540059,540037)
;

-- 2022-04-11T07:49:31.452Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540059 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T07:50:37.061Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,577559,0,10,TO_TIMESTAMP('2022-04-11 09:50:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Currency','cy.iso_code',TO_TIMESTAMP('2022-04-11 09:50:36','YYYY-MM-DD HH24:MI:SS'),100,540060,540037)
;

-- 2022-04-11T07:50:37.063Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540060 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T07:53:41.630Z
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_Details_WhereClause,SQL_From,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2022-04-11 09:53:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',1,'S','Nettobetrag der Bestellung für heute.',259,'c_order.AD_Client_ID = @AD_Client_ID@ AND c_order.AD_Org_ID=@AD_Org_ID@','c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id','cdt.name LIKE ''Bestellung''',TO_TIMESTAMP('2022-04-11 09:53:41','YYYY-MM-DD HH24:MI:SS'),100,540038)
;

-- 2022-04-11T07:54:54.566Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,541184,0,12,TO_TIMESTAMP('2022-04-11 09:54:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Net Sum','select sum(x.grandtotal) from (SELECT grandtotal FROM c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id WHERE cdt.name LIKE ''Bestellung'' )as x',TO_TIMESTAMP('2022-04-11 09:54:54','YYYY-MM-DD HH24:MI:SS'),100,540061,540038)
;

-- 2022-04-11T07:54:54.567Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540061 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T07:55:27.532Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,577559,0,10,TO_TIMESTAMP('2022-04-11 09:55:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Currency','cy.iso_code',TO_TIMESTAMP('2022-04-11 09:55:27','YYYY-MM-DD HH24:MI:SS'),100,540062,540038)
;

-- 2022-04-11T07:55:27.533Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540062 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T08:42:40.068Z
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_AllowedStaledTimeInSec,KPI_DataSource_Type,Name,Source_Table_ID,SQL_Details_WhereClause,SQL_From,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2022-04-11 10:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N',1,'S','Nettosumme der Bestellungen für diese Woche.',259,'c_order.AD_Client_ID = @AD_Client_ID@ AND c_order.AD_Org_ID=@AD_Org_ID@','c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id
             left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id
             left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
             left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id','cdt.name LIKE ''Bestellung''and co.created >= NOW()::DATE-EXTRACT(DOW FROM NOW())::INTEGER-7 AND co.created <  NOW()::DATE-EXTRACT(DOW from NOW())::INTEGER',TO_TIMESTAMP('2022-04-11 10:42:40','YYYY-MM-DD HH24:MI:SS'),100,540040)
;

-- 2022-04-11T08:44:34.762Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,541184,0,12,TO_TIMESTAMP('2022-04-11 10:44:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Net Sum','select sum(x.grandtotal)
from (SELECT grandtotal
            FROM c_order co left outer join c_doctype cdt on co.c_doctype_id = cdt.c_doctype_id
             left outer JOIN ad_clientinfo ci on co.ad_client_id = ci.ad_client_id
             left outer JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
             left outer JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id
         WHERE cdt.name LIKE ''Bestellung''and co.created >= NOW()::DATE-EXTRACT(DOW FROM NOW())::INTEGER-7 AND co.created <  NOW()::DATE-EXTRACT(DOW from NOW())::INTEGER
)as x',TO_TIMESTAMP('2022-04-11 10:44:34','YYYY-MM-DD HH24:MI:SS'),100,540065,540040)
;

-- 2022-04-11T08:44:34.764Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540065 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T08:45:26.011Z
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,577559,0,10,TO_TIMESTAMP('2022-04-11 10:45:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Currency','cy.iso_code',TO_TIMESTAMP('2022-04-11 10:45:25','YYYY-MM-DD HH24:MI:SS'),100,540066,540040)
;

-- 2022-04-11T08:45:26.014Z
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540066 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2022-04-11T08:49:28.347Z
INSERT INTO WEBUI_DashboardItem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Dashboard_ID,WEBUI_DashboardItem_ID,WEBUI_DashboardWidgetType,WEBUI_KPI_ID) VALUES (1000000,1000000,TO_TIMESTAMP('2022-04-11 10:49:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','Nettosumme der Bestellungen für heute.',20,TO_TIMESTAMP('2022-04-11 10:49:28','YYYY-MM-DD HH24:MI:SS'),100,540000,540032,'T',540038)
;

-- 2022-04-11T08:49:28.368Z
INSERT INTO WEBUI_DashboardItem_Trl (AD_Language,WEBUI_DashboardItem_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_DashboardItem_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_DashboardItem t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_DashboardItem_ID=540032 AND NOT EXISTS (SELECT 1 FROM WEBUI_DashboardItem_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_DashboardItem_ID=t.WEBUI_DashboardItem_ID)
;

-- 2022-04-11T08:49:57.225Z
INSERT INTO WEBUI_DashboardItem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Dashboard_ID,WEBUI_DashboardItem_ID,WEBUI_DashboardWidgetType,WEBUI_KPI_ID) VALUES (1000000,1000000,TO_TIMESTAMP('2022-04-11 10:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','Nettosumme der Bestellungen für diese Woche.',30,TO_TIMESTAMP('2022-04-11 10:49:57','YYYY-MM-DD HH24:MI:SS'),100,540000,540033,'T',540040)
;

-- 2022-04-11T08:49:57.227Z
INSERT INTO WEBUI_DashboardItem_Trl (AD_Language,WEBUI_DashboardItem_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_DashboardItem_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_DashboardItem t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_DashboardItem_ID=540033 AND NOT EXISTS (SELECT 1 FROM WEBUI_DashboardItem_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_DashboardItem_ID=t.WEBUI_DashboardItem_ID)
;

-- 2022-04-11T08:50:15.870Z
INSERT INTO WEBUI_DashboardItem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Dashboard_ID,WEBUI_DashboardItem_ID,WEBUI_DashboardWidgetType,WEBUI_KPI_ID) VALUES (1000000,1000000,TO_TIMESTAMP('2022-04-11 10:50:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','Nettosumme der Kundenaufträge für heute.',40,TO_TIMESTAMP('2022-04-11 10:50:15','YYYY-MM-DD HH24:MI:SS'),100,540000,540034,'T',540036)
;

-- 2022-04-11T08:50:15.878Z
INSERT INTO WEBUI_DashboardItem_Trl (AD_Language,WEBUI_DashboardItem_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_DashboardItem_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_DashboardItem t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_DashboardItem_ID=540034 AND NOT EXISTS (SELECT 1 FROM WEBUI_DashboardItem_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_DashboardItem_ID=t.WEBUI_DashboardItem_ID)
;

-- 2022-04-11T08:50:38.385Z
INSERT INTO WEBUI_DashboardItem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,WEBUI_Dashboard_ID,WEBUI_DashboardItem_ID,WEBUI_DashboardWidgetType,WEBUI_KPI_ID) VALUES (1000000,1000000,TO_TIMESTAMP('2022-04-11 10:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','Nettosumme der Kundenaufträge für diese Woche.',50,TO_TIMESTAMP('2022-04-11 10:50:38','YYYY-MM-DD HH24:MI:SS'),100,540000,540035,'T',540037)
;

-- 2022-04-11T08:50:38.394Z
INSERT INTO WEBUI_DashboardItem_Trl (AD_Language,WEBUI_DashboardItem_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_DashboardItem_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_DashboardItem t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_DashboardItem_ID=540035 AND NOT EXISTS (SELECT 1 FROM WEBUI_DashboardItem_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_DashboardItem_ID=t.WEBUI_DashboardItem_ID)
;

-- 2022-04-14T06:48:42.485Z
UPDATE WEBUI_KPI SET IsActive='N', SQL_WhereClause=null, SQL_From='de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates(true,  NOW()::date, NOW()::Date, @AD_Client_ID/-1@, @AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2022-04-14 08:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540036
;

-- 2022-04-14T06:48:56.948Z
UPDATE WEBUI_KPI_Field SET SQL_Select='TotalOrderAmt',Updated=TO_TIMESTAMP('2022-04-14 08:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540057
;

-- 2022-04-14T06:48:56.948Z
UPDATE WEBUI_KPI_Field SET SQL_Select='IsoCode',Updated=TO_TIMESTAMP('2022-04-14 08:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540058
;

-- 2022-04-14T06:49:11.457Z
UPDATE WEBUI_KPI SET IsActive='N', SQL_WhereClause=null, SQL_From='de_metas_fresh_kpi.KPI_Order_Total_Amounts_For_Current_Week(true, @AD_Client_ID/-1@, @AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2022-04-14 08:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540037
;

-- 2022-04-14T06:49:21.143Z
UPDATE WEBUI_KPI_Field SET SQL_Select='TotalOrderAmt',Updated=TO_TIMESTAMP('2022-04-14 08:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540059
;

-- 2022-04-14T06:49:21.143Z
UPDATE WEBUI_KPI_Field SET SQL_Select='IsoCode',Updated=TO_TIMESTAMP('2022-04-14 08:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540060
;

-- 2022-04-14T06:49:33.860Z
UPDATE WEBUI_KPI SET IsActive='N', SQL_WhereClause=null, SQL_From='de_metas_fresh_kpi.KPI_Order_Total_Amounts_Between_Dates(false,  NOW()::date, NOW()::Date, @AD_Client_ID/-1@, @AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2022-04-14 08:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540038
;

-- 2022-04-14T06:49:43.920Z
UPDATE WEBUI_KPI_Field SET SQL_Select='TotalOrderAmt',Updated=TO_TIMESTAMP('2022-04-14 08:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540061
;

-- 2022-04-14T06:49:43.920Z
UPDATE WEBUI_KPI_Field SET SQL_Select='IsoCode',Updated=TO_TIMESTAMP('2022-04-14 08:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540062
;

-- 2022-04-14T06:50:06.884Z
UPDATE WEBUI_KPI SET IsActive='N', SQL_WhereClause=null, SQL_From='de_metas_fresh_kpi.KPI_Order_Total_Amounts_For_Current_Week(false, @AD_Client_ID/-1@, @AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2022-04-14 08:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540040
;

-- 2022-04-14T06:50:22.315Z
UPDATE WEBUI_KPI_Field SET SQL_Select='TotalOrderAmt',Updated=TO_TIMESTAMP('2022-04-14 08:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540065
;

-- 2022-04-14T06:50:22.315Z
UPDATE WEBUI_KPI_Field SET SQL_Select='IsoCode',Updated=TO_TIMESTAMP('2022-04-14 08:50:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540066
;

-- Index on C_Order.DateOrdered for active & closed orders
-- 2022-06-06T14:55:28.163Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540698,0,259,TO_TIMESTAMP('2022-06-06 17:55:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_DateOrdered','N',TO_TIMESTAMP('2022-06-06 17:55:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-06T14:55:28.165Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540698 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-06-06T14:55:39.254Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2181,541245,540698,0,TO_TIMESTAMP('2022-06-06 17:55:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-06-06 17:55:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-06T14:57:00.078Z
UPDATE AD_Index_Table SET WhereClause='DocStatus IN (''CO'',''CL'') AND IsActive=''Y''',Updated=TO_TIMESTAMP('2022-06-06 17:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540698
;

-- 2022-06-06T14:57:00.477Z
CREATE INDEX C_Order_DateOrdered ON C_Order (DateOrdered) WHERE DocStatus IN ('CO','CL') AND IsActive='Y'
;

-- 2022-06-06T14:58:47.565Z
UPDATE AD_Index_Table SET Description='DateOrdered index for active & completed orders',Updated=TO_TIMESTAMP('2022-06-06 17:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540698
;

--TRLs
UPDATE WEBUI_DashboardItem_Trl SET Name = 'Net total of purchase orders for today.',Updated=TO_TIMESTAMP('2022-06-06 10:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' and WEBUI_DashboardItem_id=540032
;

UPDATE WEBUI_DashboardItem_Trl SET Name = 'Net total of purchase orders for this week.',Updated=TO_TIMESTAMP('2022-06-06 10:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' and WEBUI_DashboardItem_id=540033
;

UPDATE WEBUI_DashboardItem_Trl SET Name = 'Net total of sales orders for today.',Updated=TO_TIMESTAMP('2022-06-06 10:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' and WEBUI_DashboardItem_id=540034
;

UPDATE WEBUI_DashboardItem_Trl SET Name = 'Net total of sales orders for this week.',Updated=TO_TIMESTAMP('2022-06-06 10:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' and WEBUI_DashboardItem_id=540035
;

-- Increase staleTime to 1 minute, fix details filter criteria
-- 2022-06-08T07:03:48.080Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''Y'' AND DateOrdered >= NOW()::DATE AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540036
;

-- 2022-06-08T07:04:18.327Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''Y'' AND DateOrdered >= (NOW() - (DATE_PART(''dow'', NOW()) - 1) * ''1 day''::interval)::date AND DocStatus IN (''CO'', ''CL'')  AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540037
;

-- 2022-06-08T07:04:30.115Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''N'' AND DateOrdered >= NOW()::DATE AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540038
;

-- 2022-06-08T07:04:41.926Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''N'' AND DateOrdered >= (NOW() - (DATE_PART(''dow'', NOW()) - 1) * ''1 day''::interval)::date AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540040
;

Update WEBUI_KPI SET AD_Window_ID = 181,Updated=TO_TIMESTAMP('2022-06-08 12:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540038
;

Update WEBUI_KPI SET AD_Window_ID = 181,Updated=TO_TIMESTAMP('2022-06-08 12:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540040
;
