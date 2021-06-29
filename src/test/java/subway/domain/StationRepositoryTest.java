package subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Test
    public void save() {
        final Station station = new Station("잠실역");
        final Station actual = stations.save(station);
        assertThat(actual.getId()).isNotNull();
        System.out.println("id: " + actual.getId());
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    public void findByName() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findByName("잠실역");

        assertThat(station2.getId()).isNotNull();
        assertThat(station2.getName()).isEqualTo("잠실역");
        assertThat(station1).isEqualTo(station2);
        assertThat(station1).isSameAs(station2);
    }

    @Test
    void update() {
        //트랜잭션 락
        final Station station = stations.save(new Station("잠실역"));
        station.changeName("잠실나루역");
        //트랜잭션 커밋
        final Station actual = stations.findByName("잠실나루역"); //JPQL
        assertThat(actual).isNotNull();
        //트랜잭션 커밋
        //트랜잭션 언락
        final List<Station> stationList = new ArrayList(Arrays.asList(new Station("잠실역")));
        final Station station1 = stationList.get(0);
        station1.changeName("몽촌토성역");
        stationList.add(station1);
    }
}