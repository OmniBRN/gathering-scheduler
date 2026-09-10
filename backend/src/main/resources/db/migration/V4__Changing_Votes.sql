ALTER TABLE time_and_location_votes
DROP COLUMN id;

ALTER TABLE time_and_location
ADD CONSTRAINT time_and_location_id_gathering_id_key UNIQUE (id, gathering_id);

ALTER TABLE time_and_location_votes
DROP CONSTRAINT time_and_location_votes_tal_id_fkey;

ALTER TABLE time_and_location_votes
ADD CONSTRAINT time_and_location_votes_tal_id_gathering_id_fkey
FOREIGN KEY (tal_id, gathering_id) REFERENCES time_and_location(id, gathering_id) ON DELETE CASCADE;

ALTER TABLE time_and_location_votes
DROP CONSTRAINT time_and_location_votes_gathering_id_fkey;

ALTER TABLE time_and_location_votes
ALTER COLUMN tal_id SET NOT NULL,
ALTER COLUMN gathering_id SET NOT NULL;

ALTER TABLE time_and_location_votes
ADD PRIMARY KEY (tal_id, user_id);

CREATE INDEX idx_time_and_location_votes_member ON time_and_location_votes(gathering_id, user_id);
