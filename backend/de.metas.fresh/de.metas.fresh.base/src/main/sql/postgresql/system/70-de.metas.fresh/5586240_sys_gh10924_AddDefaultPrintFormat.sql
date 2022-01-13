DO $$
BEGIN
--- printformat

INSERT INTO ad_printformat (ad_printformat_id, ad_client_id, ad_org_id, isactive, created, createdby, updated,
                            updatedby, name, description, istablebased, isform, ad_table_id, ad_printpaper_id,
                            ad_printcolor_id, ad_printfont_id, isstandardheaderfooter, headermargin, footermargin,
                            createcopy, ad_reportview_id, ad_printtableformat_id, printername, isdefault,
                            jasperprocess_id, args, ad_printformat_group)
VALUES (540115, 1000000, 1000000, 'Y', '2021-04-05 09:09:54.000000 +02:00', 100, '2021-04-05 09:09:54.000000 +02:00',
        100, 'Manufacturing order label', null, 'Y', 'N', 53205, 102, 100, 540006, 'Y', 0, 0, null, null, null, null,
        'N', 584773, null, 'None');

EXCEPTION WHEN unique_violation THEN

RAISE NOTICE 'ad_printformat 540115 already exists';
end $$;



DO
$$
    DECLARE

        v_m_product_printformat_id int default 0;
        v_m_product_id int default 0;

    BEGIN

        select m_product_id
        into v_m_product_id
        from m_product
        where m_product_id = 2005577;

        select m_product_printformat_id
        into v_m_product_printformat_id
        from m_product_printformat
        where m_product_id = 2005577
          and ad_printformat_id = 540115;

        IF v_m_product_printformat_id > 0 OR v_m_product_id IS NULL THEN
			RAISE NOTICE 'Product does not exists or m_product_printformat already exists for product 2005577 and print format %', 540115;
		ELSE
            INSERT INTO m_product_printformat (ad_client_id, ad_org_id, ad_printformat_id, created, createdby,
                                               isactive,
                                               m_product_id, m_product_printformat_id, updated, updatedby)
            VALUES (1000000, 0, 540115, '2021-04-05 09:10:06.000000 +02:00', 100, 'Y', 2005577,
                    nextval('m_product_printformat_seq'),
                    '2021-04-05 09:10:06.000000 +02:00', 100);

        END IF;
    END;
$$;










