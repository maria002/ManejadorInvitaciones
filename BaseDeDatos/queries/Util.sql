SELECT * FROM Evento
WHERE TO_CHAR(FECHA, 'dd') = TO_CHAR(TO_DATE('12-DEC-2012', 'DD-Mon-YY'), 'DD')