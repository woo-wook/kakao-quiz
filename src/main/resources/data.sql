INSERT INTO COLLATERAL(COLLATERAL_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, BASE_AMOUNT, INSURE_AMOUNT, NAME)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 100, 1000000, '상해치료비');

INSERT INTO COLLATERAL(COLLATERAL_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, BASE_AMOUNT, INSURE_AMOUNT, NAME)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 100, 500000, '항공기 지연도착시 보상금');

INSERT INTO COLLATERAL(COLLATERAL_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, BASE_AMOUNT, INSURE_AMOUNT, NAME)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 38, 750000, '부분손실');

INSERT INTO COLLATERAL(COLLATERAL_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, BASE_AMOUNT, INSURE_AMOUNT, NAME)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 40, 1570000, '전체손실');

INSERT INTO COLLATERAL(COLLATERAL_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, BASE_AMOUNT, INSURE_AMOUNT, NAME)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 100, 3000000, '암진단비');

INSERT INTO PRODUCT(PRODUCT_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, MAX_CONTRACT_MONTHS, MIN_CONTRACT_MONTHS, NAME, VALIDITY_END_DATE, VALIDITY_START_DATE)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 3, 1, '여행자 보험', '2023-06-30', '2022-07-01');

INSERT INTO PRODUCT(PRODUCT_ID, CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, MAX_CONTRACT_MONTHS, MIN_CONTRACT_MONTHS, NAME, VALIDITY_END_DATE, VALIDITY_START_DATE)
VALUES (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 12, 1, '휴대폰 보험', '2023-06-30', '2022-09-01');

INSERT INTO PRODUCT_COLLATERAL(CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, COLLATERAL_ID, PRODUCT_ID)
VALUES ('unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 1, 1);

INSERT INTO PRODUCT_COLLATERAL(CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, COLLATERAL_ID, PRODUCT_ID)
VALUES ('unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 2, 1);

INSERT INTO PRODUCT_COLLATERAL(CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, COLLATERAL_ID, PRODUCT_ID)
VALUES ('unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 3, 2);

INSERT INTO PRODUCT_COLLATERAL(CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE, COLLATERAL_ID, PRODUCT_ID)
VALUES ('unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 4, 2);

INSERT INTO contract (contract_id, created_by, created_date, modified_by, modified_date, contract_months, contract_number, insure_end_date, insure_start_date, product_id, status)
values (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 2, '201908290001', '20191028', '20190829', 1, 'EXPIRY');

insert into contract_collateral (contract_collateral_id, base_amount, collateral_id, contract_id, insure_amount, name, premium)
values (default, 100, 1, 1, 1000000, '상해치료비', 20000);

INSERT INTO contract (contract_id, created_by, created_date, modified_by, modified_date, contract_months, contract_number, insure_end_date, insure_start_date, product_id, status)
values (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 2, '202207100001', '20220710', '20220909', 1, 'NORMAL');

insert into contract_collateral (contract_collateral_id, base_amount, collateral_id, contract_id, insure_amount, name, premium)
values (default, 100, 1, 2, 1000000, '상해치료비', 20000);

INSERT INTO contract (contract_id, created_by, created_date, modified_by, modified_date, contract_months, contract_number, insure_end_date, insure_start_date, product_id, status)
values (default, 'unknown', CURRENT_TIMESTAMP(), 'unknown', CURRENT_TIMESTAMP(), 2, '202207100002', '20220710', '20220909', 1, 'NORMAL');