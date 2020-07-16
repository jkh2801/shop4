package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Board;

public interface BoardMapper {

	@Select("select ifnull(max(num), 0) num from board")
	int maxnum();

	@Insert("insert into board (num, name, pass, subject, content, fileurl, regdate, readcnt, grp, grplevel, grpstep) values(#{num},#{name},#{pass},#{subject},#{content},#{fileurl},now(),#{readcnt},#{grp},#{grplevel},#{grpstep})")
	void insert(Board board);

	@Select({"<script>","select * from board ","<if test ='num != null'> where num = #{num} </if>","<if test='searchtype != null'> where ${searchtype} like '%${searchcontent}%' </if>","<if test ='start != null'> ORDER BY grp desc, grpstep LIMIT #{start}, #{limit} </if>","</script>"})
	List<Board> select(Map<String, Object> param);

	@Select({"<script>","select count(*) from board ","<if test='searchtype != null'> where ${searchtype} like '%${searchcontent}%'</if>","</script>"})
	int countnum(Map<String, Object> param);

	@Update("update board set readcnt = readcnt+1 where num = #{num}")
	void updateReadCnt(Map<String, Object> param);
	
	@Update("update board set grpstep = grpstep+1 where grp = #{grp} and grpstep > #{grpstep}")
	void grpstepAdd(Board board);

	@Update("update board set name=#{name}, pass=#{pass}, subject=#{subject}, content=#{content}, fileurl = #{fileurl} where num = #{num}")
	void update(Board board);

	@Delete("delete from board where num = #{num}")
	void delete(Map<String, Object> param);

}
