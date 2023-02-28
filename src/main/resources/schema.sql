DROP TABLE IF EXISTS weather;
CREATE TABLE weather
(
    city varchar(30) primary key not null,
    time_stamp   timestamp,
    feelsLike numeric  not null,
    temp    numeric not null,
    description varchar(200) not null
);

