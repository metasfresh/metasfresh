DROP INDEX ad_tree_name;

CREATE UNIQUE INDEX ad_tree_client_org_name
    ON ad_tree (ad_client_id, ad_org_id, name);
	
	
	DROP INDEX c_element_name;

CREATE UNIQUE INDEX c_element_name
    ON c_element (ad_client_id, ad_org_id, name);

