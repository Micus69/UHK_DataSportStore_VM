/*
Description:
This file creates database users and assigns permissions
for the sports equipment rental system.

Users:
- employee_user
  Can read, insert, and update data.

- readonly_user
  Can only read data.

Purpose:
- Basic database security
- Access control management
- Separation of user privileges
===========================================================
*/

CREATE USER IF NOT EXISTS 'employee_user'@'%' IDENTIFIED BY 'employee123';

GRANT SELECT, INSERT, UPDATE
    ON pujcovna.*
    TO 'employee_user'@'%';

CREATE USER IF NOT EXISTS 'readonly_user'@'%' IDENTIFIED BY 'readonly123';

GRANT SELECT
    ON pujcovna.*
    TO 'readonly_user'@'%';