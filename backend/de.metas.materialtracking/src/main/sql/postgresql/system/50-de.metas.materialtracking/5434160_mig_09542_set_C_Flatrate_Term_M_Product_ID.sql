
UPDATE C_Flatrate_Term ft_outer
SET M_Product_ID=data.M_Product_ID
FROM (
	SELECT ft.C_Flatrate_Term_ID, fm.M_Product_ID
	FROM C_Flatrate_Term ft
		JOIN C_Flatrate_Conditions fc ON fc.C_Flatrate_Conditions_ID=ft.C_Flatrate_Conditions_ID
		JOIN C_Flatrate_Matching fm ON fm.C_Flatrate_Conditions_ID=ft.C_Flatrate_Conditions_ID
	WHERE fc.Type_CONDITIONS='QualityBsd'
		AND ft.M_Product_ID IS NULL
) data
WHERE data.C_Flatrate_Term_ID=ft_outer.C_Flatrate_Term_ID
;
