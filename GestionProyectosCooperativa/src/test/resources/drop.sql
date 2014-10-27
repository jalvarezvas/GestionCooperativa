alter table COLABORADORES drop constraint FK_6y6dp8tgfi33gi12j5nh0wf5h if exists
alter table COLABORADORES drop constraint FK_63hjpyfewxf37rb36eklnmk11 if exists
alter table CONTRATOS drop constraint FK_m2vh7faiureuubgr84pm411xi if exists
alter table INCIDENCIAS drop constraint FK_1hycpjqfel0598c8msvhvuge4 if exists
alter table INCIDENCIAS drop constraint FK_58n2u9eja7cwgdeowiesyst5v if exists
alter table PROYECTOS drop constraint FK_o4uhvedc2hs1rxa9wm9rjlw2i if exists
alter table PROYECTOS_COLABORAN_ENTIDADES drop constraint FK_dp9j6wiq08mvtr0a0gookb938 if exists
alter table PROYECTOS_COLABORAN_ENTIDADES drop constraint FK_cfjpjkdgta2m4rykv7n30f4q4 if exists
alter table PROYECTOS_COORDINAN_SOCIOS drop constraint FK_nk8x4hk6f1u85nf6l94r0uduh if exists
alter table PROYECTOS_COORDINAN_SOCIOS drop constraint FK_cebidd3onfocaaydpvv27voac if exists
alter table PROYECTOS_PARTICIPAN_USUARIOS drop constraint FK_5itkq1hd5of5jb7qeavqbcgri if exists
alter table PROYECTOS_PARTICIPAN_USUARIOS drop constraint FK_hq6hbtb6ir62d4isd1f6f8iyl if exists
alter table SOCIOS drop constraint FK_qrsmnhdfden72l4ch5r99472x if exists
alter table TAREAS drop constraint FK_950urfyjp6f7t0u81r5wev3ew if exists
alter table TRABAJADORES drop constraint FK_epeyqewajkp0fsk1vhkncshdt if exists
alter table USUARIOS_REALIZAN_TAREAS drop constraint FK_95m6pjki1glx083nkelfvosl7 if exists
alter table USUARIOS_REALIZAN_TAREAS drop constraint FK_pp0r290ohmxomiujnqg521vem if exists
drop table COLABORADORES if exists
drop table CONTRATOS if exists
drop table ENTIDADES if exists
drop table INCIDENCIAS if exists
drop table PROYECTOS if exists
drop table PROYECTOS_COLABORAN_ENTIDADES if exists
drop table PROYECTOS_COORDINAN_SOCIOS if exists
drop table PROYECTOS_PARTICIPAN_USUARIOS if exists
drop table SOCIOS if exists
drop table TAREAS if exists
drop table TRABAJADORES if exists
drop table USUARIOS if exists
drop table USUARIOS_REALIZAN_TAREAS if exists
