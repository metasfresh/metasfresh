DROP INDEX c_elementvalue_value;
CREATE UNIQUE INDEX c_elementvalue_value
	ON c_elementvalue (c_element_id, ad_org_ID, value);