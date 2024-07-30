package atmmachine.ms;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Atm_servicepack1_impl {

	@Autowired
	ExcelReader excelReader;
	
	@Autowired
	ExcelReader2 excelReader2;
	
	@Autowired
	ExcelReader3 excelReader3;

	//"D:\\sts workspace\\ATM_Machine_MS_ExcelRead\\src\\main\\resources\\Audit-112023Amend.xlsx"
	public String getAllATMs() {
		return excelReader.readExcelAndConvertToJson(
				"D:\\sts workspace\\ATM_Machine_MS_ExcelRead\\src\\main\\resources\\Excel1-combined.xlsx");
	}
	
	//"D:\\sts workspace\\ATM_Machine_MS_ExcelRead\\src\\main\\resources\\ATM_postCodeSortCodeCapture.xlsx"
	public String getAllATMs_withCodes_() {
		return excelReader2.readExcelAndConvertToJson2(
				"D:\\sts workspace\\ATM_Machine_MS_ExcelRead\\src\\main\\resources\\Excel2-combined-pssc.xlsx");
	}
	
	public String getConnexInfo() {
		return excelReader3.readExcelAndConvertToJson3(
				"D:\\sts workspace\\ATM_Machine_MS_ExcelRead\\src\\main\\resources\\Excel3-combined-connex.xlsx");
	}
}
