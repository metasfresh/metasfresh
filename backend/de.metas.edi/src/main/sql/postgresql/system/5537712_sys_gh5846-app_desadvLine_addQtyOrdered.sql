
-- 2019-12-03T13:41:24.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-12-03 14:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569700
;

-- 2019-12-03T13:41:25.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','QtyOrdered','NUMERIC',null,null)
;

-- 2019-12-03T13:41:25.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','QtyOrdered',null,'NOT NULL',null)
;

-- 2019-12-03T13:44:15.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@QtyOrdered/0@>@MovementQty/0@',Updated=TO_TIMESTAMP('2019-12-03 14:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555772
;

-- 2019-12-03T13:45:13.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (select    CASE WHEN       SUM (l.QtyOrdered) = 0   THEN NULL    ELSE      round ((SUM (l.MovementQty)/SUM (l.QtyOrdered) ) * 100, 2)  END  from EDI_DesadvLine l where l.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID )',Updated=TO_TIMESTAMP('2019-12-03 14:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552776
;

