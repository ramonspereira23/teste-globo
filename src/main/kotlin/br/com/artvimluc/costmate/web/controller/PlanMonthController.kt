package br.com.artvimluc.costmate.web.controller;

import br.com.artvimluc.costmate.domain.PlanMonth
import br.com.artvimluc.costmate.dto.PlanMonthDTO
import br.com.artvimluc.costmate.service.PlanMonthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plan-month")
class PlanMonthController

@Autowired
constructor (
    private val planMonthService: PlanMonthService,
) {

    @GetMapping("/{referenceYear}/{referenceMonth}")
    fun get(@PathVariable referenceYear: Int, @PathVariable referenceMonth: Int): ResponseEntity<PlanMonthDTO> {
        return ResponseEntity.ok(planMonthService.findDTO(referenceYear, referenceMonth))
    }

    @PostMapping
    fun create(@RequestBody planMonth: PlanMonth): ResponseEntity<PlanMonth> {
        return ResponseEntity(planMonthService.create(planMonth), HttpStatus.CREATED)
    }

    @PostMapping("/create-next")
    fun createNextPlanMonth(): ResponseEntity<PlanMonth> {
        return ResponseEntity(planMonthService.createNextPlanMonth(), HttpStatus.CREATED)
    }

    @PutMapping("/{referenceYear}/{referenceMonth}")
    fun update(@PathVariable referenceYear: Int, @PathVariable referenceMonth: Int, @RequestBody planMonth: PlanMonth): ResponseEntity<PlanMonth> {
        return ResponseEntity(planMonthService.update(referenceYear, referenceMonth, planMonth), HttpStatus.OK)
    }

    @DeleteMapping("/{referenceYear}/{referenceMonth}")
    fun delete(@PathVariable referenceYear: Int, @PathVariable referenceMonth: Int): ResponseEntity<PlanMonth> {
        planMonthService.delete(referenceYear, referenceMonth)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}
