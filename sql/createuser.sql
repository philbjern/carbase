DROP USER 'carbaseadmin'@'%';

CREATE USER 'carbaseadmin'@'%' IDENTIFIED BY 'carbaseadmin';
GRANT ALL PRIVILEGES ON persondb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON cardb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON carsharingdb.* TO 'carbaseadmin'@'%';
GRANT ALL PRIVILEGES ON carbase.* TO 'carbaseadmin'@'%';
