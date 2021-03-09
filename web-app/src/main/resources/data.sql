INSERT INTO ROUTE (NUMBER_ROUTE, LENGTH, LAP_TIME, NUMBER_OF_STOPS)
VALUES (2, 12.5, 48, 14),
       (5, 8, 36, 8),
       (12, 5.4, 37, 12),
       (7, 11.3, 43, 11);

INSERT INTO TRANSPORT (TRANSPORT_TYPE, FUEL_TYPE, REGISTER_NUMBER, CAPACITY, DATE_OF_MANUFACTURE, NUMBER_ROUTE)
VALUES ('BUS', 'ELECTRIC', '1243 AB-1', 75, '2018-08-01', 2),
       ('BUS', 'GASOLINE', '2545 AK-1', 68, '2020-12-01', 2),
       ('TROLLEY', 'ELECTRIC', '4545 AX-1', 48, '2004-12-25', 5),
       ('TRAM', 'ELECTRIC', '9999 AA-1', 85, '2020-12-01', 12),
       ('TRAM', 'ELECTRIC', '7777 BZ-1', 85, '2018-07-01', 12),
       ('BUS', 'GASOLINE', '1243 AB-2', 45, '2020-12-01', 5),
       ('BUS', 'GASOLINE', '2569 MD-2', 67, '2020-12-01', 7),
       ('BUS', 'GASOLINE', '4785 MC-2', 96, '2020-12-01', 7);
