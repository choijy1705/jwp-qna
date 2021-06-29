package subway.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineRepositoryTest {
    @Autowired
    private StationRepository stations;

    @Autowired
    private LineRepository lines;

    @Test
    public void saveWithLine() {
        final Station station = new Station("잠실역");
        station.setLine(lines.save(new Line("2호선")));
        //station.setLine(new Line("2호선")); //영속화 되지 않은 Line은 가질수 없다.
        /*
        insert into station(id, name) value(0, "잠실역");
         */
        stations.save(station);
    }

    @Test
    public void findByNameWithLine() {
        final Station actual = stations.findByName("교대역");
        assertThat(actual).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    public void removeLine() {
        final Station actual = stations.findByName("교대역");
        actual.setLine(null);
    }

    @Test
    public void findById() {
        final Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
        assertThat(line.getStations().get(0).getName()).isEqualTo("교대역");
    }

    @Test
    public void getStations() {
        final Line line = lines.findByName("3호선");
        line.addStation(stations.save(new Station("옥수역")));
    }

    @Test
    public void addStation() {
        final Line line = lines.findByName("3호선");
        final Station station = stations.save(new Station("옥수역"));
        line.addStation(station);
        assertThat(station.getLine()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        stations.flush();
    }
}