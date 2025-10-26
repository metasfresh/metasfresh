DROP INDEX ad_tree_name;

CREATE UNIQUE INDEX ad_tree_client_org_name
    ON ad_tree (ad_client_id, ad_org_id, name);