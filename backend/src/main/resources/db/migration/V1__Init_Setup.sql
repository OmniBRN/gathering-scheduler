CREATE TABLE gathering (
    id uuid PRIMARY KEY,
    gathering_name text NOT NULL
);

CREATE TABLE gathering_members (
    id uuid NOT NULL,
    gathering_id uuid NOT NULL references gathering(id) ON DELETE CASCADE,
    creator boolean NOT NULL,
    member_name text NOT NULL,
    salt text NOT NULL,
    hashed_pin text NOT NULL,
    PRIMARY KEY (gathering_id, id)
);

CREATE INDEX idx_gathering_members_gathering_id ON gathering_members(gathering_id);

CREATE TABLE time_and_location (
    id uuid PRIMARY KEY,
    gathering_id uuid NOT NULL references gathering(id) ON DELETE CASCADE,
    user_id uuid NOT NULL,
    start_time timestamptz,
    end_time timestamptz,
    gathering_location_longitude decimal(9,6) NOT NULL,
    gathering_location_latitude decimal(8,6) NOT NULL,
    primary_location boolean NOT NULL,
    votes integer NOT NULL DEFAULT 0,
    FOREIGN KEY (gathering_id, user_id) references gathering_members(gathering_id, id) ON DELETE CASCADE,
    CHECK (end_time > start_time),
    CHECK (gathering_location_latitude BETWEEN -90.0 AND 90.0),
    CHECK (gathering_location_longitude BETWEEN -180.0 AND 180.0)
);

CREATE TABLE time_and_location_votes (
    id uuid PRIMARY KEY,
    tal_id uuid references time_and_location(id) ON DELETE CASCADE,
    gathering_id uuid references gathering(id) ON DELETE CASCADE,
    user_id uuid NOT NULL,
    FOREIGN KEY (gathering_id, user_id) references gathering_members(gathering_id, id) ON DELETE CASCADE
);

CREATE INDEX idx_time_and_location_member ON time_and_location(gathering_id, user_id);