--
-- Data for Name: dataentry_group; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_group (ad_client_id, ad_org_id, created, createdby, dataentry_group_id, dataentry_targetwindow_id, description, isactive, name, tabname, updated, updatedby, seqno) VALUES (1000000, 1000000, '2019-03-07 08:04:00+00', 100, 100000, 123, 'Description of Example-Group1', 'N', 'Example-Group1', 'Group1-Tab1', '2019-03-07 09:29:43+00', 100, 10);


--
-- Data for Name: dataentry_subgroup; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_subgroup (ad_client_id, ad_org_id, created, createdby, dataentry_group_id, dataentry_subgroup_id, description, isactive, name, tabname, updated, updatedby, seqno) VALUES (1000000, 1000000, '2019-03-07 08:04:03+00', 100, 100000, 100000, 'Description for SubGroup1-1', 'Y', 'SubGroup1-1', 'Group1-Tab1-SubTab1', '2019-03-07 08:04:04+00', 100, 10);
INSERT INTO public.dataentry_subgroup (ad_client_id, ad_org_id, created, createdby, dataentry_group_id, dataentry_subgroup_id, description, isactive, name, tabname, updated, updatedby, seqno) VALUES (1000000, 1000000, '2019-03-07 08:04:06+00', 100, 100000, 100001, 'Description for SubGroup1-2', 'Y', 'SubGroup1-2', 'Group1-Tab1-SubTab2', '2019-03-07 09:30:49+00', 100, 20);


--
-- Data for Name: dataentry_section; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_section (ad_client_id, ad_org_id, created, createdby, dataentry_section_id, description, isactive, isinitiallyopen, name, sectionname, updated, updatedby, seqno, isinitiallyclosed, dataentry_subgroup_id) VALUES (1000000, 1000000, '2019-03-07 09:31:42+00', 100, 100002, NULL, 'Y', 'Y', 'SubGroup2-Section1', 'SubGroup2-Section1', '2019-03-07 09:31:54+00', 100, 10, 'N', 100001);
INSERT INTO public.dataentry_section (ad_client_id, ad_org_id, created, createdby, dataentry_section_id, description, isactive, isinitiallyopen, name, sectionname, updated, updatedby, seqno, isinitiallyclosed, dataentry_subgroup_id) VALUES (1000000, 1000000, '2019-03-07 08:04:11+00', 100, 100000, 'Section with 3 lines; in the 1st, just one col is used; in the 2nd, one field is long-text, yet the two fields of the 3rd line shall be alligned!', 'Y', 'Y', 'Section1-1 1551945835494', 'Section1-1 1551945835494', '2019-03-07 09:34:50+00', 100, 10, 'Y', 100000);
INSERT INTO public.dataentry_section (ad_client_id, ad_org_id, created, createdby, dataentry_section_id, description, isactive, isinitiallyopen, name, sectionname, updated, updatedby, seqno, isinitiallyclosed, dataentry_subgroup_id) VALUES (1000000, 1000000, '2019-03-07 08:05:18+00', 100, 100001, 'Section with one line; its two columns shall appear to be aligned with the first section', 'Y', 'Y', 'Section1-2 1551945835494', 'Section1-2 1551945835494', '2019-03-07 09:36:42+00', 100, 20, 'Y', 100000);


--
-- Data for Name: dataentry_line; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 08:04:15+00', 100, 100000, 100000, 'Y', 10, '2019-03-07 08:04:15+00', 100);
INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 08:04:16+00', 100, 100001, 100000, 'Y', 20, '2019-03-07 08:04:16+00', 100);
INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 08:04:18+00', 100, 100002, 100000, 'Y', 30, '2019-03-07 08:04:18+00', 100);
INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 08:05:23+00', 100, 100003, 100001, 'Y', 10, '2019-03-07 08:05:23+00', 100);
INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 09:31:55+00', 100, 100004, 100002, 'Y', 10, '2019-03-07 09:31:55+00', 100);
INSERT INTO public.dataentry_line (ad_client_id, ad_org_id, created, createdby, dataentry_line_id, dataentry_section_id, isactive, seqno, updated, updatedby) VALUES (1000000, 1000000, '2019-03-07 09:32:13+00', 100, 100005, 100002, 'Y', 20, '2019-03-07 09:32:13+00', 100);


--
-- Data for Name: dataentry_field; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:04:25+00', 100, 100000, 'B', 'Yes-No, single field in its line', 'Y', 'Y', 'Tab1-Section1-Line1-Field1', 'P', 10, '2019-03-07 08:04:28+00', 100, 100000);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:04:36+00', 100, 100001, 'LT', 'LongText, first field in its line', 'Y', 'N', 'Tab1-Section1-Line2-Field2', 'P', 10, '2019-03-07 08:04:38+00', 100, 100001);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:04:46+00', 100, 100002, 'B', 'Yes-No, second fields in its line', 'Y', 'N', 'Tab1-Section1-Line2-Field3', 'P', 20, '2019-03-07 08:04:49+00', 100, 100001);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:04:58+00', 100, 100003, 'T', 'Text, first field in its line', 'Y', 'N', 'Tab1-Section1-Line3-Field4', 'P', 10, '2019-03-07 08:05:00+00', 100, 100002);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:05:10+00', 100, 100004, 'T', 'Text, second field in its line', 'Y', 'N', 'Tab1-Section1-Line3-Field5', 'P', 20, '2019-03-07 08:05:12+00', 100, 100002);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:05:34+00', 100, 100005, 'D', 'Tab1-Section2-Field1 Description', 'Y', 'Y', 'Tab1-Section2-Line1-Field1', 'NP', 10, '2019-03-07 08:05:38+00', 100, 100003);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 08:05:49+00', 100, 100006, 'L', 'Tab1-Section2-Field2 Description', 'Y', 'N', 'Tab1-Section2-Line1-Field2', 'NP', 20, '2019-03-07 08:05:52+00', 100, 100003);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 09:32:48+00', 100, 100007, 'N', NULL, 'Y', 'N', 'Line10-Field1', 'NP', 10, '2019-03-07 09:32:55+00', 100, 100004);
INSERT INTO public.dataentry_field (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_recordtype, description, isactive, ismandatory, name, personaldatacategory, seqno, updated, updatedby, dataentry_line_id) VALUES (1000000, 1000000, '2019-03-07 09:33:15+00', 100, 100008, 'B', NULL, 'Y', 'N', 'Line10-Field2', 'NP', 20, '2019-03-07 09:33:18+00', 100, 100004);


--
-- Data for Name: dataentry_listvalue; Type: TABLE DATA; Schema: public; Owner: metasfresh
--

INSERT INTO public.dataentry_listvalue (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_listvalue_id, isactive, name, seqno, updated, updatedby, description) VALUES (1000000, 1000000, '2019-03-07 08:05:54+00', 100, 100006, 100000, 'Y', 'ListItem 2', 10, '2019-03-07 08:05:56+00', 100, 'ListItem 2 with SeqNo10');
INSERT INTO public.dataentry_listvalue (ad_client_id, ad_org_id, created, createdby, dataentry_field_id, dataentry_listvalue_id, isactive, name, seqno, updated, updatedby, description) VALUES (1000000, 1000000, '2019-03-07 08:05:59+00', 100, 100006, 100001, 'Y', 'ListItem 1', 20, '2019-03-07 08:06:00+00', 100, 'ListItem 1 with SeqNo20');


--
-- PostgreSQL database dump complete
--

