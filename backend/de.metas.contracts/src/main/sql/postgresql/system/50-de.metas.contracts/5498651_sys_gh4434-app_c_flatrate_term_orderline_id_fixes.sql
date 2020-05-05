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
			AND ft_inner.path @@> ARRAY[ft_outer.c_flatrate_term_ID]
			AND ft_outer.C_OrderLine_Term_ID IS NULL
		LIMIT 5000
	) data
	WHERE data.ft_to_update_c_flatrate_term_ID = ft_outer.c_flatrate_term_ID
');
