DROP TABLE INVITADO CASCADE CONSTRAINTS;
DROP TABLE EVENTO CASCADE CONSTRAINTS;
DROP TABLE INVITACION CASCADE CONSTRAINTS;
DROP TABLE USUARIO CASCADE CONSTRAINTS;
DROP TABLE PERFIL_USUARIO CASCADE CONSTRAINTS;

DROP SEQUENCE sec_Id_Perfil_Usuario;
DROP SEQUENCE sec_IdUsuarios;
DROP SEQUENCE sec_Id_evento;
DROP SEQUENCE sec_Id_invitado;
DROP SEQUENCE sec_Id_invitacion;

create table Perfil_Usuario(
Id int not null primary key,
Nombre varchar2(15) not null,
Activo char(1) default '1' not null,
CONSTRAINT check_activo_perfil_usuario CHECK (Activo IN ('1', '0'))
);

create sequence sec_Id_Perfil_Usuario
start with 1
increment by 1; 

create table Usuario(
Id int not null primary key,
Nombre varchar2(50) not null,
Apellido varchar2(50) not null,
Cuenta varchar2(25) not null,
Clave varchar2(100) not null,
Activo char(1) default '1' not null,
Id_Perfil_Usuario int not null,
CONSTRAINT check_activo_usuario CHECK (Activo IN ('1', '0')),
CONSTRAINT CuentaUnique UNIQUE (Cuenta)
);

alter table Usuario
add constraint FK_UsuariosPerfilID
foreign key (Id_Perfil_Usuario)
references Perfil_Usuario(Id);

create sequence sec_IdUsuarios
start with 1
increment by 1;

create table Evento (
Id_evento int not null primary key,
Nombre varchar2(1000) not null,
Fecha date not null,
Activo char(1) default '1' not null
CONSTRAINT check_activo_evento CHECK (Activo IN ('1', '0')),
Ubicacion varchar2(1000) not null
);

create sequence sec_Id_evento
start with 1
increment by 1;

create table Invitado (
Id_Invitado int not null primary key,
Nombre varchar2(50) not null,
Apellido varchar2(50) not null,
Telefono varchar2(20) not null,
Direccion varchar2(1000) not null,
Activo char(1) default '1' not null
CONSTRAINT check_activo_invitado CHECK (Activo IN ('1', '0')),
Sexo char(1) not null);

alter table Invitado
add constraint CK_InvitadoMoF
check (sexo = 'M' or sexo = 'F');

create sequence sec_Id_invitado
start with 1
increment by 1;

create table Invitacion (
Id_Invitacion int not null primary key,
Fecha_Asistencia date not null,
Razon_visita varchar2(1000),
Id_evento int not null,
Id_invitado int not null,
Activo char(1) default '1' not null
CONSTRAINT check_activo_invitacion CHECK (Activo IN ('1', '0')),
Id_usuario int not null
);

alter table Invitacion
add constraint FK_InvitacionEventoId
foreign key (Id_evento)
references Evento(Id_evento);

alter table Invitacion
add constraint FK_InvitacionInvitadoId
foreign key (Id_invitado)
references Invitado(Id_invitado);

alter table Invitacion
add constraint FK_InvitacionUsuarioId
foreign key (Id_usuario)
references Usuario(Id);

create sequence sec_Id_invitacion
start with 1
increment by 1;