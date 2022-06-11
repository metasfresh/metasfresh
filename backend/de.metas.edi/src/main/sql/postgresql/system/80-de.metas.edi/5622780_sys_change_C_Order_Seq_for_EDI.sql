
select setval('c_order_seq',60000000);

comment on sequence c_order_seq is 'We send the C_Order_ID to as "seller''s order reference" in our EDI INVOICs.
One customer requests this ID in "his" INVOICs to consist of 8 digits and start with a six.
That is why we set this sequence to 6xxxxxxx';
