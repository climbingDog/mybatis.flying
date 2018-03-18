package indi.mybatis.flying.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import indi.mybatis.flying.ApplicationContextProvider;
import indi.mybatis.flying.pojo.Role2_;
import indi.mybatis.flying.service2.Role2Service;

@MappedTypes({ Role2_.class })
@MappedJdbcTypes({ JdbcType.INTEGER })
public class Role2TypeHandler extends BaseTypeHandler<Role2_> implements TypeHandler<Role2_> {

	@Override
	public Role2_ getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		return null;
	}

	@Override
	public Role2_ getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public Role2_ getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1, Role2_ arg2, JdbcType arg3) throws SQLException {
		if (arg2 != null) {
			arg0.setString(arg1, arg2.getId().toString());
		}
	}
}
