package fr.sabb.data.mapper;

import fr.sabb.data.object.Match;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MatchMapper extends SabbMapper<Match> {

	@Select("SELECT * FROM sabb.match ORDER BY id")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="opponent", column="opponent"),
			@Result(property="matchDate", column="match_date"),
			@Result(property="team", column="id_team", one=@One(select="getTeam")),
			@Result(property="idFFBB", column="id_ffbb"),
			@Result(property="home", column="home"),
			@Result(property="locationSwitched", column="location_switched"),
	})
	List<Match> getAll();
	
	@Select("SELECT * FROM sabb.match WHERE id_ffbb=#{idFFBB} AND id_team=#{team.id}")
	@Results(value= {
			@Result(property="team", column="id_team", one=@One(select="getTeam"))
	})
	Match getMatch(Match match);
	
	@Insert("INSERT INTO sabb.match(opponent,id_team,match_date,id_ffbb,home,location_switched) VAlUES(#{opponent}, #{team.id}, #{matchDate}, #{idFFBB}, #{home}, #{locationSwitched})")
	void insert(Match match);
	
	@Update("UPDATE sabb.match SET opponent=#{opponent}, id_team=#{team.id}, match_date=#{matchDate}, home=#{home}, location_switched=#{locationSwitched} WHERE id=#{id}")
	void update(Match match);
}
