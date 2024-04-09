package sh.demo.employee.utils;

import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OperationConstants {

	Map<String, Operator> operations = new HashMap<>();

	public OperationConstants() {
		operations.put("EQ", Ops.EQ);
		operations.put("GT", Ops.GT);
		operations.put("LT", Ops.LT);
		operations.put("GTE", Ops.GOE);
		operations.put("LTE", Ops.LOE);
	}
}
