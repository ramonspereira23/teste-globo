package br.com.artvimluc.costmate.web.controller;

import br.com.artvimluc.costmate.domain.Income
import br.com.artvimluc.costmate.domain.PlanMonth
import br.com.artvimluc.costmate.dto.PlanMonthDTO
import br.com.artvimluc.costmate.service.IncomeService
import br.com.artvimluc.costmate.service.PlanMonthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/income")
class IncomeController

@Autowired
constructor (
    private val incomeService: IncomeService,
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<Income> {
        return ResponseEntity.ok(incomeService.find(id))
    }
    @PostMapping
    fun create(@RequestBody income: Income): ResponseEntity<Income> {
        return ResponseEntity(incomeService.create(income), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody income: Income): ResponseEntity<Income> {
        return ResponseEntity(incomeService.update(id, income), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Income> {
        incomeService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}
