SELECT * FROM Evento
WHERE TO_CHAR(FECHA, 'dd') = TO_CHAR(TO_DATE('12-DEC-2012', 'DD-Mon-YY'), 'DD')

SELECT * FROM Evento WHERE TO_CHAR(FECHA, 'dd') = TO_CHAR(TO_DATE('Sun Nov 25 10:49:16 CLST 2012', 'DD-Mon-YY'), 'DD')



ALTER TABLE Invitacion
MODIFY Fecha_Asistencia date null;