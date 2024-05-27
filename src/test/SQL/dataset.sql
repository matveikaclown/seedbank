-- Inserting into "family" table
insert into "family" (name_of_family)
values
    ('Rosaceae'), ('Fabaceae'), ('Asteraceae'),
    ('Orchidaceae'), ('Poaceae'), ('Rubiaceae'),
    ('Lamiaceae'), ('Brassicaceae'), ('Solanaceae'),
    ('Apiaceae');

-- Inserting into "genus" table
insert into "genus" (family_id, name_of_genus)
values
    (1, 'Rosa'), (1, 'Rubus'), (1, 'Prunus'),
    (2, 'Trifolium'), (2, 'Medicago'), (2, 'Pisum'),
    (3, 'Aster'), (3, 'Bellis'), (3, 'Chrysanthemum'),
    (4, 'Orchis'), (4, 'Dactylorhiza'), (4, 'Vanilla'),
    (5, 'Poa'), (5, 'Bambusa'), (5, 'Zea'),
    (6, 'Coffea'), (6, 'Gardenia'), (6, 'Cinchona'),
    (7, 'Mentha'), (7, 'Salvia'), (7, 'Thymus'),
    (8, 'Brassica'), (8, 'Raphanus'), (8, 'Armoracia'),
    (9, 'Solanum'), (9, 'Nicotiana'), (9, 'Capsicum'),
    (10, 'Daucus'), (10, 'Apium'), (10, 'Petroselinum');

-- Inserting into "specie" table
insert into "specie" (genus_id, name_of_specie)
values
    (1, 'Rosa canina'), (1, 'Rosa gallica'), (1, 'Rosa damascena'),
    (2, 'Rubus idaeus'), (2, 'Rubus fruticosus'), (2, 'Rubus chamaemorus'),
    (3, 'Prunus domestica'), (3, 'Prunus avium'), (3, 'Prunus persica'),
    (4, 'Trifolium repens'), (4, 'Trifolium pratense'), (4, 'Trifolium dubium'),
    (5, 'Medicago sativa'), (5, 'Medicago lupulina'), (5, 'Medicago minima'),
    (6, 'Pisum sativum'), (6, 'Pisum fulvum'), (6, 'Pisum arvense'),
    (7, 'Aster amellus'), (7, 'Aster alpinus'), (7, 'Aster novi-belgii'),
    (8, 'Bellis perennis'), (8, 'Bellis sylvestris'), (8, 'Bellis annua'),
    (9, 'Chrysanthemum indicum'), (9, 'Chrysanthemum morifolium'), (9, 'Chrysanthemum coronarium'),
    (10, 'Orchis mascula'), (10, 'Orchis militaris'), (10, 'Orchis italica'),
    (11, 'Dactylorhiza fuchsii'), (11, 'Dactylorhiza maculata'), (11, 'Dactylorhiza incarnata'),
    (12, 'Vanilla planifolia'), (12, 'Vanilla pompona'), (12, 'Vanilla phaeantha'),
    (13, 'Poa annua'), (13, 'Poa pratensis'), (13, 'Poa trivialis'),
    (14, 'Bambusa vulgaris'), (14, 'Bambusa oldhamii'), (14, 'Bambusa balcooa'),
    (15, 'Zea mays'), (15, 'Zea diploperennis'), (15, 'Zea perennis'),
    (16, 'Coffea arabica'), (16, 'Coffea canephora'), (16, 'Coffea liberica'),
    (17, 'Gardenia jasminoides'), (17, 'Gardenia taitensis'), (17, 'Gardenia brighamii'),
    (18, 'Cinchona officinalis'), (18, 'Cinchona pubescens'), (18, 'Cinchona ledgeriana'),
    (19, 'Mentha spicata'), (19, 'Mentha piperita'), (19, 'Mentha aquatica'),
    (20, 'Salvia officinalis'), (20, 'Salvia splendens'), (20, 'Salvia pratensis'),
    (21, 'Thymus vulgaris'), (21, 'Thymus serpyllum'), (21, 'Thymus pulegioides'),
    (22, 'Brassica oleracea'), (22, 'Brassica napus'), (22, 'Brassica rapa'),
    (23, 'Raphanus sativus'), (23, 'Raphanus raphanistrum'), (23, 'Raphanus caudatus'),
    (24, 'Armoracia rusticana'), (24, 'Armoracia lapathifolia'), (24, 'Armoracia macrocarpa'),
    (25, 'Solanum tuberosum'), (25, 'Solanum lycopersicum'), (25, 'Solanum melongena'),
    (26, 'Nicotiana tabacum'), (26, 'Nicotiana rustica'), (26, 'Nicotiana sylvestris'),
    (27, 'Capsicum annuum'), (27, 'Capsicum frutescens'), (27, 'Capsicum chinense'),
    (28, 'Daucus carota'), (28, 'Daucus pusillus'), (28, 'Daucus glochidiatus'),
    (29, 'Apium graveolens'), (29, 'Apium nodiflorum'), (29, 'Apium leptophyllum'),
    (30, 'Petroselinum crispum'), (30, 'Petroselinum segetum'), (30, 'Petroselinum hortense');

insert into "red_list" (category)
values
    ('NE - угроза не оценивалась'), ('DD - для оценки угрозы недостаточно данных'), ('LC - вызывающие наименьшие опасения'),
    ('NT - близкие к уязвимому положению'), ('VU - уязвимые'), ('EN - вымирающие'),
    ('CR - находящиеся на грани полного исчезновения'), ('EW - исчезнувшие в дикой природе'), ('EX - исчезнувшие');


insert into "book_level" (level_of_book)
values
    ('Самарская Область'), ('Российская Федерация');

insert into "red_book" (book_level_id, category)
values
    (1, '0 - вероятно, исчезнувшие'), (1, '1 - находящиеся под угрозой исчезновения'), (1, '2 - сокращающиеся в численности'),
    (1, '3 - редкие'), (1, '4 - неопределенные по статусу'), (1, '5 - восстанавливаемые и восстанавливающиеся'),

    (2, '0 - вероятно исчезнувшие'), (2, '1 - находящиеся под угрозой исчезновения'), (2, '2 - cокращающиеся в численности'),
    (2, '3 - редкие'), (2, '4 - неопределенные по статусу'), (2, '5 - восстанавливаемые и восстанавливающиеся');

insert into "place_of_collection" (place_of_collection)
values
    ('Чапаевск'), ('Безенчук'), ('Самара'), ('Новокуйбышевск'), ('Тольятти'),
    ('Жигулевск'), ('Сызрань'), ('Кошки'), ('Шентала'), ('Кинель');

INSERT INTO "ecotop" (name_of_ecotop)
VALUES
    ('Лес'),
    ('Степь'),
    ('Тундра'),
    ('Болото'),
    ('Пустыня'),
    ('Река'),
    ('Озеро'),
    ('Скала'),
    ('Горы'),
    ('Тайга');

insert into "role" (name_of_role)
values
    ('ADMIN'), ('USER');

insert into "account" (role_id, first_name, last_name, login, password, patronymic, active)
values
    (1, 'Матвей', 'Привалов', 'matveika', '$2a$05$lSvDbE/g/K.YsOCQBrNYY.bQv3CylXF.3cs8ZEPrg1aT6XFWi96oO', 'Алексеевич', true),
    (2, 'Александр', 'Пушкин', 'first', '$2a$05$lqydbs.cJkhD8MiLu1HUQOqWMf2KIrouSbZ60TsxiKiLL2IbyqG0S', 'Сергеевич', true),
    (2, 'Лиля', 'Брик', 'second', '$2a$05$GVWzM/8b68PRQokPIqFV1OwNdbu4HXscWOBDAXU4h8t18ojplInze', 'Юрьевна', true),
    (1, 'Павел', 'Нахимов', 'psnahimov', '$2a$05$lSvDbE/g/K.YsOCQBrNYY.bQv3CylXF.3cs8ZEPrg1aT6XFWi96oO', 'Степанович', true);

insert into "seed" (account_id, ecotop_id, place_of_collection_id, red_book_rf_id, red_book_so_id, red_list_id, seed_id, specie_id, completed_seeds, pest_infestation, seed_germination, seed_moisture, date_of_collection, gpsaltitude, weight_of1000seeds, number_of_seeds, gpslatitude, gpslongitude, comment, seed_name, is_hidden)
values
    (2, 1, 1, 1, 1, 1, '1-9-2021-0', 1, '87.6', '7.49', '50', '75', now(), '1', '2600', '665', '33', '1', 'Скрыто', 'Seed 1', true),
    (2, 1, 1, 1, 1, 2, '2-1-2022-20', 2, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'Не скрыто', 'Seed 2', false),
    (3, 1, 2, 1, 1, 3, '10-1-1901-12', 3, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'Скрыто', 'Seed 3', true),
    (2, 1, 1, 1, 1, 4, '3-1-2021-13', 4, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 4', false),
    (2, 1, 1, 1, 1, 5, '4-6-2021-0', 5, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'Сделать кое что', 'Seed 5', false),
    (2, 1, 1, 1, 1, 5, '10-1-1900-12', 6, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'надо исправить', 'Seed 6', true),
    (2, 1, 1, 5, 1, 4, '5-7-2021-0', 7, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'завтра доделаю', 'Seed 7', false),
    (2, 1, 1, 5, 1, 3, '5-7-2021-0-1', 8, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'Переделать', 'Seed 8', false),
    (2, 1, 1, 5, 1, 2, '6-1-2022-24', 9, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'без комментариев', 'Seed 9', false),
    (2, 1, 1, 5, 1, 1, '7-3-2021-0', 10, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'Надо удалить', 'Seed 10', false),
    (2, 1, 1, 2, 1, 1, '10-1-1902-12', 11, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 11', false),
    (2, 1, 1, 2, 1, 2, '10-1-1903-12', 12, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 12', false),
    (2, 1, 1, 2, 1, 3, '8-1-2021-0', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 13', false),
    (2, 1, 1, 2, 1, 4, '9-4-2021-0', 14, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 14', false),
    (2, 1, 1, 2, 1, 5, '9-4-2020-0', 15, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 15', false),
    (2, 1, 1, 2, 1, 5, '10-1-2022-12', 16, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 16', false),
    (2, 1, 1, 2, 2, 4, '10-1-1904-12', 17, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 17', false),
    (2, 1, 1, 5, 2, 3, '10-1-1905-12', 18, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 18', false),
    (2, 1, 1, 5, 2, 2, '10-1-1906-12', 19, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 19', false),
    (2, 1, 1, 5, 2, 1, '10-1-1907-12', 20, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 20', false),
    (2, 1, 1, 5, 2, 1, '10-1-1908-12', 21, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 21', false),
    (2, 1, 1, 5, 2, 2, '10-1-1909-12', 22, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 22', false),
    (2, 1, 1, 5, 3, 3, '10-1-1910-12', 23, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 23', false),
    (2, 1, 1, 3, 3, 4, '90-1-2022-12', 24, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 24', false),
    (2, 1, 1, 3, 3, 5, '91-1-2022-12', 25, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 25', false),
    (2, 1, 1, 3, 3, 2, '92-1-2022-12', 26, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 26', false),
    (2, 1, 1, 3, 3, 2, '93-1-2022-12', 27, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 27', false),
    (2, 1, 1, 3, 3, 2, '94-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 28', false),
    (2, 1, 1, 3, 3, 2, '95-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 29', false),
    (2, 1, 1, 3, 3, 2, '96-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 30', false),
    (2, 1, 1, 3, 3, 2, '97-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 31', false),
    (2, 1, 1, 5, 3, 2, '98-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 32', false),
    (2, 1, 1, 5, 3, 2, '99-1-2022-12', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', '', 'Seed 33', false),
    (2, 1, 1, 5, 3, 2, '10-1-2022-12-1', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 34', false),
    (2, 1, 1, 5, 4, 2, '10-1-2022-1000', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 35', false),
    (2, 1, 1, 5, 4, 2, '10-1-2022-1010', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 36', false),
    (2, 1, 1, 5, 4, 1, '10-1-2022-1020', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 37', false),
    (2, 1, 1, 5, 4, 1, '10-1-2022-1030', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 38', false),
    (2, 1, 1, 5, 4, 1, '10-1-2022-1040', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 39', false),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1050', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 40', false),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1060', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 41', false),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1070', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 42', true),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1080', 26, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 43', false),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1090', 26, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 44', false),
    (2, 1, 1, 4, 4, 1, '10-1-2022-1100', 26, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 45', false),
    (2, 1, 1, 4, 4, 1, '9999-9999-9999-9999', 26, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 46', false),
    (2, 1, 1, 4, 4, 2, '9999-9999-9999-9999-1', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 47', false),
    (2, 1, 1, 4, 4, 2, '9999-9999-9999-9999-2', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 48', false),
    (2, 1, 1, 5, 4, 2, '9999-9999-9999-9999-3', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 49', false),
    (2, 1, 1, 5, 5, 2, '9999-9999-9999-9999-4', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 50', false),
    (2, 1, 9, 5, 5, 2, '1-2-3-4', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 51', false),
    (2, 1, 1, 5, 5, 2, '5-6-7-8', 13, '87.6', '7.49', '50', '75', now(), '1', '2560', '665', '33', '1', 'NONE1', 'Seed 52', false);

insert into "field" (field)
values
    ('id'), ('seedName'), ('family'),
    ('genus'), ('specie'), ('redList'),
    ('redBookRF'), ('redBookSO'), ('dateOfCollection'),
    ('placeOfCollection'), ('weightOf1000Seeds'), ('numberOfSeeds'),
    ('completedSeeds'), ('seedGermination'), ('seedMoisture'),
    ('GPS'),('ecotop'), ('pestInfestation'),
    ('comment'), ('photoSeed'), ('photoXRay'),
    ('photoEcotop');

insert into "seed_field" (seed_id, field_id)
values
    ('1-2-3-4', 21), ('1-2-3-4', 22), ('1-2-3-4', 19), ('1-2-3-4', 3)

/*SELECT pg_size_pretty( pg_database_size( 'seedbank' ) );*/