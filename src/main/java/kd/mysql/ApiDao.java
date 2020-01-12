package kd.mysql;

import kd.mysql.domain.FmBoard;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApiDao {
    protected static final String NAMESPACE = "kd.mysql.ApiDao";

    @Autowired
    private SqlSession sqlSession;

    public String selectName(){
        String a = sqlSession.selectOne(NAMESPACE + ".getTime");
        return a;
    }

    public int save(FmBoard fmBoard){
        int a = sqlSession.insert(NAMESPACE + ".setBoard", fmBoard);
        return a;
    }

    public int saveContent(FmBoard fmBoard){
        return sqlSession.insert(NAMESPACE + ".saveContent", fmBoard);

    }

    public List<FmBoard> fmBoards(){
        return sqlSession.selectList(NAMESPACE + ".getFmBoards");

    }
}