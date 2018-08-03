
--
-- Drop the UC and recreate a "normal" index
--

-- 2018-08-01T11:41:20.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540192
;

-- 2018-08-01T11:41:20.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540192
;

DROP INDEX IF EXISTS public.c_flatrate_term_orderlineterm;

CREATE INDEX c_flatrate_term_orderlineterm
  ON public.c_flatrate_term
  USING btree
  (c_orderline_term_id);

-- async-update existing records, 5000 at a time
select "de.metas.async".executesqlasync('
	UPDATE c_flatrate_term ft_outer 
	SET C_OrderLine_Term_ID = data.C_OrderLine_Term_ID, updated=now(), updatedby=99
	FROM 
	(
		select 
			ft_outer.c_flatrate_term_ID as ft_to_update_c_flatrate_term_ID, 
			ft_inner.c_flatrate_term_ID, ft_inner.C_OrderLine_Term_ID, ft_inner.path
		FROM 
			c_flatrate_term ft_outer,
			(
			select ft.c_flatrate_term_ID, ft.C_OrderLine_Term_ID, hierarchy.path
			from c_flatrate_term ft
				JOIN de_metas_contracts.fetchflatratetermhierarchy_byc_flatrate_term_id(ft.C_Flatrate_Term_ID) hierarchy ON true
			where C_OrderLine_Term_ID is not null
			) ft_inner 

		WHERE true
			AND ft_inner.path @> ARRAY[ft_outer.c_flatrate_term_ID]
			AND ft_outer.C_OrderLine_Term_ID IS NULL
		LIMIT 5000;
	) data
	WHERE data.ft_to_update_c_flatrate_term_ID = ft_outer.c_flatrate_term_ID;
');

--
-- this was "@IsAutoRenew@='Y'"
-- @ExtensionType/None@='EA' | @ExtensionType/None@='EO'-- 2018-08-01T14:10:51.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExtensionType/None@=''EA'' | @ExtensionType/None@=''EO''',Updated=TO_TIMESTAMP('2018-08-01 14:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548482
;

