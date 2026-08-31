package ro.tudorboureanu.gatheringschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import ro.tudorboureanu.gatheringschedule.Gathering.Gathering;

@JsonTest
public class GatheringScheduleJsonTest {
    @Autowired
    private JacksonTester<Gathering> json;


    @Test
    void gatheringSerializationTest() throws IOException {

        UUID gatheringId = UUID.fromString("51bf4f02-98af-471e-ab60-8fa3307dd6c2");
        String gatheringName = "Movie Night";

        Gathering gathering = new Gathering(gatheringId, gatheringName);

        assertThat(json.write(gathering)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(gathering)).hasJsonPathStringValue("@.id");
        assertThat(json.write(gathering)).extractingJsonPathStringValue("@.id").isEqualTo("51bf4f02-98af-471e-ab60-8fa3307dd6c2");
        assertThat(json.write(gathering)).hasJsonPathStringValue("@.gatheringName");
        assertThat(json.write(gathering)).extractingJsonPathStringValue("@.gatheringName").isEqualTo("Movie Night");
    }

    @Test
    void gatheringDeserializationTest() throws IOException {

        String expected = """
                {
                    "id" : "51bf4f02-98af-471e-ab60-8fa3307dd6c2",
                    "gatheringName" : "Movie Night"
                }
                """;

        UUID gatheringId = UUID.fromString("51bf4f02-98af-471e-ab60-8fa3307dd6c2");
        String gatheringName = "Movie Night";

        assertThat(json.parse(expected)).isEqualTo(new Gathering(gatheringId, gatheringName));
        assertThat(json.parseObject(expected).getId()).isEqualTo(gatheringId);
        assertThat(json.parseObject(expected).getGatheringName()).isEqualTo(gatheringName);
    }
}