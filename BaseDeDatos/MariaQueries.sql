--Cantidad de personas que asistieron a cada evento
-- select ID_EVENTO, count(*) as cantidadAsistencia from INVITACION
-- where Activo = 1
-- group by ID_EVENTO;


-- --% de las personas en la lista que asistieron a cada evento
-- select ID_EVENTO, count(*) as cantidadAsistencia from INVITACION
-- group by ID_EVENTO;


--eventos más visitados





--eventos más visitados por mujeres
select ID_EVENTO, count(*) as EventosMasVisitados from evento
where sexo = 'f' natural join usuario; 


