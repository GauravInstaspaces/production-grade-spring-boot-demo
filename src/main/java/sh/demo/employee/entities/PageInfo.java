package sh.demo.employee.entities;

import lombok.Data;

@Data
public class PageInfo {

	private Long total;

	private Integer limit;

	private Integer offset;
}
